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
        CRITERIA VARBIT(15),
        TYPE VARBIT(1),
        WORKLOAD DECIMAL(5,2)
    )`);
}


function randInt(max, min) {
    return Math.floor(Math.random() * (max - min + 1) + min);
  }
function genRandomCriteria()
{
    let randomCriteria = ['1','1','1','1','1','1','1','1','1','1','1','1','1','1','1'];
    let zerosCount = randInt(-1, 6);
    for (let i = 0; i < zerosCount; i++)
    {
        const position = randInt(-1, 15);
        randomCriteria[position] = '0';
    }
    return randomCriteria.join('');
}
async function writeRawPoints()
{
    const atms = require('./raw_data/atms.json').atms;
    const offices = require('./raw_data/offices.json');
    let i = 0;
    for (let atm of atms)
    {
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
        $5::VARBIT(15),
        $6::VARBIT(1),
        $7::DECIMAL(5,2)
        )`,[atm.address, atm.latitude, atm.longitude, atm.allDay,
            criteria, 0, 0]) 
        // todo : think about time
        console.log(i)
        i++
    }
   

    for (let office of offices)
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
        $5::VARBIT(15),
        $6::VARBIT(1),
        $7::DECIMAL(5,2)
        )`,[office.address, office.latitude, office.longitude, 
            {openHours: office.openHours, openHoursIndividual: office.openHoursIndividual},
            criteria, 1, Math.random() * 100]) 
        console.log(i)
        i++
    }
    
        
}
async function filterPoints(criteria, latitude, longitude)
{
    if (criteria.length != 15) return;

    return client.query('select * from points where (criteria & $1::varbit(15)) = $1::varbit(15) order by ((latitude - $2) ^ 2 + (longitude - $3) ^ 2) LIMIT 10', [criteria, latitude, longitude]);
}
module.exports = {filterPoints}     

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