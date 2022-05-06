const { default: mongoose } = require('mongoose');

const apartmentTypeModel = mongoose.Schema({
  type: { type: String, require: true },
});

module.exports = mongoose.model(
  'apartmentType',
  apartmentTypeModel,
  'apartment_types'
);
