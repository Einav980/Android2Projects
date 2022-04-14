const express = require('express');
const router = express.Router();
const apartmentController = require('../controllers/apartment.controller');

router.get('/apartments', apartmentController.listApartments);
router.get('/apartments/:id', apartmentController.getApartment);

module.exports = router;
