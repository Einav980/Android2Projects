const express = require('express');
const router = express.Router();
const watchlistController = require('../controllers/watchlist.controller');

router.get('/watchlist', watchlistController.listWatchlistItems);
router.get('/watchlist/user/:email', watchlistController.getWatchListbyUserId);
router.post('/watchlist/add', watchlistController.addWatchListApartment);
router.post('/watchlist/remove', watchlistController.removeWatchListApartment);

module.exports = router;
