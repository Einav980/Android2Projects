const express = require('express');
const router = express.Router();
const apartmentController = require('../controllers/apartment.controller');

router.get('/apartments', apartmentController.listApartments);
router.get('/apartments/user/:userid',apartmentController.getApartmentbyUserId);
router.get('/apartments/:id', apartmentController.getApartmentById);
router.post('/apartments/add', apartmentController.addApartment);
router.delete('/apartments/:id',apartmentController.deleteApartment);
router.put('/apartments/:id/status',apartmentController.editApartmentstatus);
router.put('/apartments/:id',apartmentController.editApartment);

module.exports = router;
