import java.util.UUID

if (context.valueFor('request', 'player') == 'Nikita') return '9999'
if (context.valueFor('request', 'player') == 'Eugene') return UUID.randomUUID().toString()