const { default: mongoose } = require('mongoose');

const apartmentModel = mongoose.Schema({
  city: { type: String, require: true },
  numberOfRooms: { type: Number, require: true },
  price: { type: Number, require: true },
  numberOfBaths: {type: Number, require: false}, 
  address: { type: String, require: true },
  lat: { type: Number, require: true },
  lng: { type: Number, require: true },
  size: { type: Number, require: true },
  type: { type: String, require: true },
  imageUrl: { type: String, require: true },
  status: {type: String , require: true}
});

module.exports = mongoose.model('apartments', apartmentModel, 'apartments');
