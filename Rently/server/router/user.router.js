const express = require('express');
const router = express.Router();
const userController = require('../controllers/user.controller');

router.get('/users', userController.listUsers);
router.get('/users/:email', userController.getUser);
router.post('/users/signup', userController.signUpUser);
router.post('/users/login', userController.loginUser);

module.exports = router;
