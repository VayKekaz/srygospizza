export function dateToString(date: Date): string {
    // format: "HH:MM dd.M.yyyy"
    // format: "13:43 22.7.2021"
    return `${date.getHours()}:${date.getMinutes()} ${date.getDate()}.${date.getMonth() + 1}.${date.getFullYear()}`
}
