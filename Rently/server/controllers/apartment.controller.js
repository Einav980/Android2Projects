const { useParams } = require('react-router-dom');
const Apartment = require('../models/apartment.model');

const listApartments = async (req, res) => {
  try {
    const apartments = await Apartment.find({});
    res.send(apartments);
  } catch (error) {
    res.status(400).json({ error: error });
  }
};

const getApartmentById = async (req, res) => {
  try {
    const { id } = req.params;
    const apartment = await Apartment.findById(id);
    res.send(apartment);
  } catch (error) {
    res.status(400).json({ error: 'Apartment not found' });
  }
};

const getApartmentbyUserId = async (req, res) => {
  try {
    const { userid } = req.params;
    const apartments = await Apartment.find({ userId: userid });
    res.send(apartments);
  } catch (error) {
    res.status(400).json({ error: 'No apartments found' });
  }
};

module.exports = {
  listApartments,
  getApartmentById,
  getApartmentbyUserId,
};
