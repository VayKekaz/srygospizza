export function removeAllNonNumericFrom(string) {
  string = string.replace(/,/, '.');
  string = string.replace(/[^\d.-]/g, '');
  return string;
}

export function dishModelAsKotlinObjectSyntaxString({ title, description, price, image }) {
  // language=kotlin
  return `Dish(
name="${title}",
description="${description}",
price=${price}f,
image="${image}",
),`
      .replace(/\n/g, "")
}
