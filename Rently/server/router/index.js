const apartmentRouter = require('./apartment.router');
const userRouter = require('./user.router');
const apartmentTypeRouter = require('./apartmentType.router');
const watchlistRouter = require('./watchlist.router');

const routers = { apartmentRouter, userRouter, apartmentTypeRouter, watchlistRouter };

module.exports = routers;
