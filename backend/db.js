const pg = require('pg')
const config = require('./db-config.json')
const client = new pg.Client(config)
client.connect();

async function createPointsTable()
{
    client.query(`CREATE TABLE POINTS (
        ADRESS TEXT,
        LATITUDE DECIMAL(8,6),
        LONGITUDE DECIMAL(9,6),
        OPENING TIMESTAMP,
        CLOSING TIMESTAMP,
        CRITERIA VARCHAR(15),
        TYPE VARCHAR(1),
        WORKLOAD DECIMAL(5,2)
    )`)
}



module.exports = {createPointsTable}

async function initDatabase()
{
    Promise.all([
        createPointsTable()
    ]).then((res) =>
    {
        console.log('done initDatabase')
    })
}

if (process.argv[2] == 'startAll')
{
    initDatabase();
}