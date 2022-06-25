const { default: mongoose } = require('mongoose');

const apartmentModel = mongoose.Schema({
  userId: { type: String, require: true },
  description: { type: String, require: true },
  city: { type: String, require: true },
  numberOfBeds: { type: Number, require: true },
  numberOfBaths: { type: Number, require: false },
  price: { type: Number, require: true },
  address: { type: String, require: true },
  Location: { type: { lat: Number, lng: Number }, require: true },
  size: { type: Number, require: true },
  type: { type: String, require: true },
  imageUrl: { type: String, require: true },
  status: { type: String, require: true },
  hasBalcony: { type: Boolean, require: true },
  hasParking: { type: Boolean, require: true },
  isFurnished: { type: Boolean, require: true },
  isAnimalFriendly: { type: Boolean, require: true },
});

module.exports = mongoose.model('apartment', apartmentModel, 'apartments');
