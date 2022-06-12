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

const addApartment = async (req, res) => {
  try {
    const { city, numberOfRooms, price, numberOfBaths, address, lat, lng, size, type, imageUrl, status, userId } = req.body;

    const newApartment = await Apartment.create({
      city: city,
      numberOfRooms: numberOfRooms,
      price: price,
      numberOfBaths: numberOfBaths,
      address: address,
      lat: lat,
      lng: lng,
      size: size,
      type: type,
      imageUrl: imageUrl,
      status: status,
      userId: userId,
    });

    newApartment.save();
    res.status(201).send({
      returnCode: 201,
      message: 'Successfully created new apartment',
      type: 'Success',
    });

  } catch (error) {
    console.log(error);
    res.status(400).json({
      returnCode: 400,
      message: 'Error while adding apartment',
      type: 'Error',
    });
  }
};

module.exports = {
  listApartments,
  getApartmentById,
  getApartmentbyUserId,
  addApartment,
};
