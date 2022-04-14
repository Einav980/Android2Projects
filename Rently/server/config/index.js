const mongoose = require('mongoose');
const username = 'rently';
const password = 'PpJObxeyTQCkqc4U';
const uri = `mongodb+srv://${username}:${password}@tomsmongo.jsldc.mongodb.net/rently?retryWrites=true&w=majority`;
mongoose.connect(uri).catch((e) => {
  console.error('Connection error', e.message);
});

const db = mongoose.connection;

module.exports = db;
