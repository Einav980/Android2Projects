const { default: mongoose } = require('mongoose');

const userModel = mongoose.Schema({
  email: { type: String, require: true },
  lastname: { type: String, require: true },
  firstname: { type: String, require: true },
  hashedPassword: { type: String, require: true },
  phone: { type: String, require: true },
  type: { type: String, require: true },
});

module.exports = mongoose.model('User', userModel, 'users');
