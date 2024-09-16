static java.lang.Integer parseIntWithDefault(String str, int defaultValue) {
    return str?.isInteger() ? str.toInteger() : defaultValue
}

diceOne = parseIntWithDefault(context.valueFor('request', 'diceOne'), 0)
diceTwo = parseIntWithDefault(context.valueFor('request', 'diceTwo'),0)

return diceOne + diceTwo