const db = require('./db')
const express = require('express')
const app = express();




app.post('/destination', express.json(), async (req, res) =>
{
    const {time, criteria, latitude, longitude} = req.body;
    if (!time || !criteria || !latitude || !longitude)
    {
        
        res.send('json must have time_ms, criteria, latitude, longtitude fields')
        return;
    }
    try
    {
        const answer = await db.filterPoints(req.body);
        res.json(answer.rows)
        console.log(req.body)
        console.log(answer)
        console.log('answer_rowCount: ', answer.rowCount)
    } catch (err)
    {
        res.send("unknown error");
        console.log(err)
    }
   
    
})

app.listen(8080)