const { default: mongoose } = require('mongoose');

const watchListModel = mongoose.Schema({
  email: { type: String, require: true },
  apartmentId: { type: String, require: true },
});

module.exports = mongoose.model('watchlist', watchListModel, 'watch_list');
