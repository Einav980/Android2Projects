const WatchList = require('../models/watchlist.model');
const Apartment = require('../models/apartment.model');

const listWatchlistItems = async (req, res) => {
  try {
    const watchlistItems = await WatchList.find({});
    res.send(watchlistItems);
  } catch (error) {
    res.status(400).json({ error: "Couldn't fetch watchlist" });
  }
};

const listUserWatchlistItems = async (req, res) => {
  const { userid } = req.params;
  try {
    const watchlistItems = await WatchList.find({ email: userid });
    res.send(watchlistItems);
  } catch (error) {
    res.status(400).json({ error: "Couldn't list user watchlist items" });
  }
};

const getWatchListbyUserId = async (req, res) => {
  try {
    const { email } = req.params;
    const watchListApartmentIds = await WatchList.find({ email: email });

    const apartmentsID = watchListApartmentIds.map(function (watch) {
      return watch.apartmentId;
    });

    console.log('AparmentsId', apartmentsID);
    const apartments = await Apartment.find({
      _id: { $in: apartmentsID },
      status: 'Available',
    });
    res.send(apartments);
  } catch (error) {
    res.status(400).json({ error: 'No apartments found' });
  }
};

const addWatchListApartment = async (req, res) => {
  try {
    const { email, apartmentId } = req.body;

    const newWatchList = await WatchList.create({
      email: email,
      apartmentId: apartmentId,
    });

    newWatchList.save();
    res.status(201).send({
      returnCode: 201,
      message: 'Successfully added apartment to watchlist',
      type: 'Success',
    });
  } catch (error) {
    console.log(error);
    res.status(400).json({
      returnCode: 400,
      message: 'Error while adding apartment to watchlist',
      type: 'Error',
    });
  }
};

const removeWatchListApartment = async (req, res) => {
  const { email, apartmentId } = req.body;
  try {
    const result = await WatchList.findOneAndDelete({
      email: email,
      apartmentId: apartmentId,
    });

    res.status(201).send({
      returnCode: 201,
      message: 'Successfully removed apartment from watchlist',
      type: 'Success',
    });
  } catch (error) {
    res.status(400).json({
      returnCode: 400,
      message: 'Error while removing apartment from watchlist',
      type: 'Error',
    });
  }
};

module.exports = {
  removeWatchListApartment,
  getWatchListbyUserId,
  addWatchListApartment,
  listWatchlistItems,
  listUserWatchlistItems,
};
