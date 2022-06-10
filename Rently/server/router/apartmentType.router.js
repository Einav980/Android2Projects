const express = require('express');
const router = express.Router();
const apartmentTypeController = require('../controllers/apartmentType.controller');

router.get('/apartments/types', apartmentTypeController.listApartmentTypes);
router.post(
  '/apartments/types/:type',
  apartmentTypeController.addApartmentType
);
router.delete(
  '/apartments/types/:typeId',
  apartmentTypeController.deleteApartmentType
);

module.exports = router;
