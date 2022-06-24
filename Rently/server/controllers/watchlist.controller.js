const WatchList = require('../models/watchlist.model');
const Apartment = require('../models/apartment.model');


const getWatchListbyUserId = async (req, res) => {
  try {
    const { email } = req.params;
    const watchListApartmentIds = await WatchList.find({email:email});

    const apartmentsID =watchListApartmentIds.map(function(watch){
      return watch.apartmentId;
    })
    const apartments = await Apartment.find({_id :{ $in: apartmentsID }})
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
      apartmentId: apartmentId
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
      apartmentId: apartmentId
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
};
