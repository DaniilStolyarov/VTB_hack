const db = require('./db')
const express = require('express')
const app = express();




app.post('/destination', express.json(), async (req, res) =>
{
    if (!req.body.criteria) 
    {
        res.send('err: no criteria specified')
        return;
    }
    const answer = await db.filterPoints(req.body.criteria);
    res.json(answer.rows)
    console.log(req.body)
    console.log('answer: ')
    console.log(JSON.stringify(answer.rows.slice(0, 5)))
})

app.listen(8080)