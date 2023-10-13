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
    res.json(JSON.stringify(answer.rows))
    console.log(req.body)
})

app.listen(8080)