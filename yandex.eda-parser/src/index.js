import { JSDOM } from "jsdom";
import { writeFile } from "fs";
import { dishModelAsKotlinObjectSyntaxString, removeAllNonNumericFrom } from "./util.js";

const DISH_ELEMENT_CLASSNAME = "RestaurantPageMenuItem_root";
const TITLE_CLASSNAME = "RestaurantPageMenuItem_title";
const PRICE_CLASSNAME = "Price_root";
const DESCRIPTION_CLASSNAME = "HTMLDescription_root RestaurantPageMenuItem_description";
const IMAGE_CLASSNAME = "UIMagicalImage_image RestaurantPageMenuItem_pictureImage";

function selector(classnames) {
  classnames = classnames.split(/(\s+)/).filter(e => e.trim().length > 0);
  return "." + classnames.join(".");
}

let dishModels = [];

const dom = await JSDOM.fromFile("./src/yandex.eda.html")
const document = dom.window.document;
const dishElements = document.querySelectorAll(selector(DISH_ELEMENT_CLASSNAME));
console.log(`Found ${dishElements.length} dishes.`);
dishElements.forEach(element => {
  const titleElement = element.querySelector(selector(TITLE_CLASSNAME));
  const priceElement = element.querySelector(selector(PRICE_CLASSNAME));
  const descriptionElement = element.querySelector(selector(DESCRIPTION_CLASSNAME));
  const imageElement = element.querySelector(selector(IMAGE_CLASSNAME));
  let imageUrl = undefined;
  if (imageElement !== null) {
    const urlImageFunction = imageElement.style.background;
    if (urlImageFunction.startsWith("url(")) {
      imageUrl = urlImageFunction.substring(4, urlImageFunction.length - 1);
    } else {
      console.error(`Non-standard image style of element named "${titleElement.textContent}":`);
      console.error(imageElement.style);
    }
  }
  const dishModel = {
    title: titleElement !== null ? titleElement.textContent : undefined,
    price: priceElement !== null ? removeAllNonNumericFrom(priceElement.textContent) : undefined,
    description: descriptionElement !== null ? descriptionElement.textContent : "",
    image: imageUrl,
  };
  dishModels.push(dishModel);
});

let ktArrayString = '';
dishModels.forEach(model => {
  const { title, description, price, image } = model;
  if (title === undefined || price === undefined || image === undefined)
    return;
  ktArrayString += dishModelAsKotlinObjectSyntaxString(model);
});

writeFile(
    "./dishModels.kt",
    // language=kotlin
    `val MOCK_DISHES = listOf(${ktArrayString})`,
    //JSON.stringify(dishModels),
    error => error && console.error(error),
)

console.debug("Dish models:");
console.debug(dishModels);
