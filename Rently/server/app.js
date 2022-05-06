// Routers
const routers = require('./router');
const express = require('express');
const app = express();
const port = 3000;
const db = require('./config');

var bodyParser = require('body-parser');
var jsonParser = bodyParser.json();

db.on('connected', () => {
  console.log('Connected to the database!');
}).on('error', () => {
  console.log('Error connecting to the database');
});

app.use('/api', jsonParser, routers.apartmentTypeRouter);
app.use('/api', routers.apartmentRouter);
app.use('/api', jsonParser, routers.userRouter);

app.listen(port, () => {
  console.log(`App listening on port ${port}`);
});
