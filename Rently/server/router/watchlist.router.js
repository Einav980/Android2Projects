const express = require('express');
const router = express.Router();
const watchlistController = require('../controllers/watchlist.controller');

router.get('/watchlists/user/:email',watchlistController.getWatchListbyUserId);
router.post('/watchlists/add', watchlistController.addWatchListApartment);
router.delete('/watchlists/remove',watchlistController.removeWatchListApartment);

module.exports = router;
