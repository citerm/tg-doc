protected java.lang.Boolean isUndefined(java.lang.String player) {
    return context.valueFor(player, 'name') == 'undefined'
}

if (isUndefined('playerOne')) return 0
if (isUndefined('playerTwo')) return 1
if (isUndefined('playerThree')) return 2
if (isUndefined('playerFour')) return 3
if (isUndefined('playerFive')) return 4
return 5