const pg = require('pg')
const config = require('./db-config.json')
const client = new pg.Client(config)
client.connect();

async function createPointsTable()
{
    return client.query(`CREATE TABLE POINTS (
        ID SERIAL,
        ADRESS TEXT,
        LATITUDE DECIMAL(8,6),
        LONGITUDE DECIMAL(9,6),
        WORKTIME JSON,
        CRITERIA VARCHAR(15),
        TYPE VARCHAR(1),
        WORKLOAD DECIMAL(5,2)
    )`);
}


function randInt(max, min) {
    return Math.floor(Math.random() * (max - min + 1) + min);
  }
function genRandomCriteria()
{
    let randomCriteria = (randInt(2, Math.pow(2, 15) - 1)).toString(2);
    while (randomCriteria.length < 15)
    {
        randomCriteria = '0' + randomCriteria;
    }
    return randomCriteria;
}
async function writeRawPoints()
{
    const atms = require('./raw_data/atms.json').atms;
    const offices = require('./raw_data/offices.json');

    atms.forEach(async atm => {
        const criteria = '111000000000000';
        await client.query(`INSERT INTO POINTS(
            ADRESS,
            LATITUDE,
            LONGITUDE,
            WORKTIME,
            CRITERIA,
            TYPE,
            WORKLOAD
        ) VALUES(
        $1::TEXT,
        $2::DECIMAL(8,6),
        $3::DECIMAL(9,6),
        $4::JSON,
        $5::VARCHAR(15),
        $6::VARCHAR(1),
        $7::DECIMAL(5,2)
        )`,[atm.address, atm.latitude, atm.longitude, atm.allDay,
            criteria, 0, 0]) 
        // todo : think about time
    });

    offices.forEach(async office =>
        {   
        const criteria = genRandomCriteria();
        await client.query(`INSERT INTO POINTS(
            ADRESS,
            LATITUDE,
            LONGITUDE,
            WORKTIME,
            CRITERIA,
            TYPE,
            WORKLOAD
        ) VALUES(
        $1::TEXT,
        $2::DECIMAL(8,6),
        $3::DECIMAL(9,6),
        $4::JSON,
        $5::VARCHAR(15),
        $6::VARCHAR(1),
        $7::DECIMAL(5,2)
        )`,[office.address, office.latitude, office.longitude, 
            {openHours: office.openHours, openHoursIndividual: office.openHoursIndividual},
            criteria, 1, Math.random() * 100]) 
        })
        
}

module.exports = {createPointsTable}

async function initDatabase()
{
    Promise.all([
        createPointsTable(),
        writeRawPoints()
    ]).then((res) =>
    {
        console.log('done initDatabase')
    })
}

if (process.argv[2] == 'startAll')
{
    initDatabase();
} 
else if (process.argv[2] == 'restartAll')
{
    client.query('drop table points').finally((res) => {
        initDatabase();
    })
}