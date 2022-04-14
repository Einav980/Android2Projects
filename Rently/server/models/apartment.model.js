const { default: mongoose } = require('mongoose');

const apartmentModel = mongoose.Schema({
  city: { type: String, require: true },
  numberOfRooms: { type: Number, require: true },
  price: { type: Number, require: true },
});

module.exports = mongoose.model('apartments', apartmentModel, 'apartments');
