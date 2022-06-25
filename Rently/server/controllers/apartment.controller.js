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
    console.log('Body', req.body);

    const {
      city,
      description,
      numberOfRooms,
      price,
      numberOfBaths,
      address,
      location,
      hasBalcony,
      hasParking,
      isFurnished,
      isAnimalFriendly,
      size,
      type,
      imageUrl,
      status,
      userId,
    } = req.body;

    const newApartment = await Apartment.create({
      city,
      description,
      numberOfRooms,
      numberOfBaths,
      price,
      address,
      location,
      size,
      type,
      imageUrl,
      status,
      userId,
      hasBalcony,
      hasParking,
      isAnimalFriendly,
      isFurnished,
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

const deleteApartment = async (req, res) => {
  var { id } = req.params;
  try {
    const result = await Apartment.findByIdAndDelete(id);
    res.send(result);
  } catch (error) {
    res.status(400).json({
      returnCode: 400,
      message: 'Error while deleting apartment',
      type: 'Error',
    });
  }
};

const editApartmentstatus = async (req, res) => {
  try {
    const { status } = req.body;
    var { id } = req.params;
    const updatedResult = await Apartment.findByIdAndUpdate(id, {
      status: status,
    });

    console.log(updatedResult);

    res.status(201).send({
      returnCode: 201,
      message: 'Successfully edited apartment status ',
      type: 'Success',
    });
  } catch (error) {
    console.log(error);
    res.status(400).json({
      returnCode: 400,
      message: 'Error while editing apartment status',
      type: 'Error',
    });
  }
};

const editApartment = async (req, res) => {
  try {
    const {
      city,
      numberOfRooms,
      price,
      numberOfBaths,
      address,
      lat,
      lng,
      size,
      type,
      imageUrl,
      status,
      userId,
    } = req.body;
    var { id } = req.params;
    const updatedResult = await Apartment.findByIdAndUpdate(id, {
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

    console.log(updatedResult);

    res.status(201).send({
      returnCode: 201,
      message: 'Successfully edited apartment ',
      type: 'Success',
    });
  } catch (error) {
    console.log(error);
    res.status(400).json({
      returnCode: 400,
      message: 'Error while editing apartment ',
      type: 'Error',
    });
  }
};

module.exports = {
  listApartments,
  getApartmentById,
  getApartmentbyUserId,
  addApartment,
  deleteApartment,
  editApartmentstatus,
  editApartment,
};
