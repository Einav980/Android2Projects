const express = require('express');
const router = express.Router();
const apartmentController = require('../controllers/apartment.controller');

router.get('/apartments', apartmentController.listApartments);
router.get(
  '/apartments/user/:userid',
  apartmentController.getApartmentbyUserId
);
router.get('/apartments/:id', apartmentController.getApartmentById);
router.post('/apartments/add', apartmentController.addApartment);

module.exports = router;
