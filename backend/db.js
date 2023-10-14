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
        CRITERIA VARBIT(15),
        TYPE VARBIT(1),
        WORKLOAD DECIMAL(5,2),
        OPEN_TIME TIME,
        CLOSE_TIME TIME)`);
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
            CRITERIA,
            TYPE,
            WORKLOAD,
            OPEN_TIME,
            CLOSE_TIME
        ) VALUES(
        $1::TEXT,
        $2::DECIMAL(8,6),
        $3::DECIMAL(9,6),
        $4::VARBIT(15),
        $5::VARBIT(1),
        $6::DECIMAL(5,2),
        $7::TIME,
        $8::TIME
        )`,[atm.address, atm.latitude, atm.longitude,
            criteria, 0, 0, "00:00", "23:59:59"]) 
        // todo : think about time
        console.log(i)
        i++
    }
   

    for (let office of offices)
    {
        const criteria = genRandomCriteria();
        const officeTime = ["8:00-20:00", "9:00-21:00", "10:00-17:00"]
        await client.query(`INSERT INTO POINTS(
            ADRESS,
            LATITUDE,
            LONGITUDE,
            CRITERIA,
            TYPE,
            WORKLOAD,
            OPEN_TIME,
            CLOSE_TIME
        ) VALUES(
        $1::TEXT,
        $2::DECIMAL(8,6),
        $3::DECIMAL(9,6),
        $4::VARBIT(15),
        $5::VARBIT(1),
        $6::DECIMAL(5,2),
        $7::TIME,
        $8::TIME
        )`,[office.address, office.latitude, office.longitude, 
            criteria, 1, Math.random() * 100,
            officeTime[i % 3].split('-')[0], officeTime[i % 3].split('-')[1]]
        )
        console.log(i)
        i++
    }
    
        
}
async function filterPoints({criteria, latitude, longitude, onlyDepartments, time})
{
    if (criteria.length !== 15) return;
    if (onlyDepartments)
    {
        // select * from points where (criteria & $1::varbit(15)) = $1::varbit(15) AND type = \'1\'::varbit(1) order by |/((latitude - $2) ^ 2 + (longitude - $3) ^ 2) LIMIT 10
        // 
        return client.query(`select *,
        $4::time + ('1 hour'::interval * (sqrt((100 * (latitude - 55.660496)) ^ 2  + (30075 * (longitude - 37.474543) / 360)^2) / 15 + workload / 100)) as estimated
        from points where (criteria & $1::varbit(15)) = $1::varbit(15) AND (type = '1'::varbit(1))
        and ($4::time + ('1 hour'::interval * (sqrt((100 * (latitude - 55.660496)) ^ 2  + (30075 * (longitude - 37.474543) / 360)^2) / 15 + workload / 100)) < CLOSE_TIME)
        and ($4::time + ('1 hour'::interval * (sqrt((100 * (latitude - 55.660496)) ^ 2  + (30075 * (longitude - 37.474543) / 360)^2) / 15 + workload / 100)) > OPEN_TIME)
         order by |/((latitude - $2) ^ 2 + (longitude - $3) ^ 2) LIMIT 10`, [criteria, latitude, longitude, time]);
    }
    else
    {
        return client.query('select * from points where (criteria & $1::varbit(15)) = $1::varbit(15) order by |/((latitude - $2) ^ 2 + (longitude - $3) ^ 2) LIMIT 10', [criteria, latitude, longitude]);
    }
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