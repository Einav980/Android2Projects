const { useParams } = require('react-router-dom');
const ApartmentType = require('../models/apartmentType.model');
const { toTitleCase } = require('../utils');

const listApartmentTypes = async (req, res) => {
  try {
    const types = await ApartmentType.find({});
    res.send(types);
  } catch (error) {
    res.status(401).json({ error: error });
  }
};

const addApartmentType = async (req, res) => {
  var { type } = req.body;
  type = toTitleCase(type);
  try {
    const apartmentType = await ApartmentType.findOne({ type: type });
    if (!apartmentType) {
      const newApartmentType = new ApartmentType({
        type: type,
      });
      newApartmentType.save((error, apartmentType) => {
        if (error) {
          res.status(400).json({ error: error });
        }
        res.send(newApartmentType);
      });
    } else {
      res.json({
        returnCode: 405,
        message: 'Type already exists',
        type: 'Error',
      });
    }
  } catch (error) {
    res.status(400).json({ error: error });
  }
};

const deleteApartmentType = async (req, res) => {
  var { typeId } = req.params;
  try {
    const result = await ApartmentType.findByIdAndDelete(typeId);
    res.send(result);
  } catch (error) {
    res.status(400).json({ error: error });
  }
};

module.exports = {
  listApartmentTypes,
  addApartmentType,
  deleteApartmentType,
};
