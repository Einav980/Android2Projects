const User = require('../models/user.model');
const bcrypt = require('bcryptjs');

const listUsers = async (req, res) => {
  try {
    const users = await User.find({});
    res.send(users);
  } catch (error) {
    res.status(400).json({ error: error });
  }
};

const getUser = async (req, res) => {
  try {
    const { email } = req.params;
    const user = await User.findOne({ email: email });
    res.send(user);
  } catch (error) {
    res.status(400).json({ error: 'User not found' });
  }
};

const loginUser = async (req, res) => {
  try {
    const { email, password } = req.body;
    const user = await User.findOne({ email: email });
    if (!user) {
      res.json({
        returnCode: 404,
        message: 'Username was not found!',
        type: 'Error',
      });
    }
    const doesPasswordMatch = bcrypt.compareSync(password, user.hashedPassword);
    if (!doesPasswordMatch) {
      res.json({
        returnCode: 401,
        message: 'Invalid credentials',
        type: 'Error',
      });
    } else {
      res.json({
        returnCode: 200,
        message: 'Logged in succesfully',
        type: 'Success',
      });
    }
  } catch (error) {
    res.status(400).json({
      returnCode: 400,
      message: 'Error while trying to login',
      type: 'Error',
    });
  }
};

const signUpUser = async (req, res) => {
  try {
    const { email, password, phone, firstname, lastname } = req.body;
    const user = await User.findOne({ email: email });
    if (!user) {
      const hashedPassword = bcrypt.hashSync(password, await bcrypt.genSalt());
      const newUser = await User.create({
        email: email,
        firstname: firstname,
        lastname: lastname,
        hashedPassword: hashedPassword,
        phone: phone,
        type: "Normal"
      });

      newUser.save();
      res.status(201).send({
        returnCode: 201,
        message: 'Successfully signed up',
        type: 'Success',
      });
    } else {
      res.json({
        returnCode: 405,
        message: 'Email is already in use',
        type: 'Error',
      });
    }
  } catch (error) {
    console.log(error);
    res.status(400).json({
      returnCode: 400,
      message: 'Error while signing up',
      type: 'Error',
    });
  }
};

const editUser = async (req, res) => {
  try {
    const { phone, firstname, lastname } = req.body;
    var { email } = req.params;
    const updatedResult = await User.findOneAndUpdate({ email: email },
      {
        firstname: firstname,
        lastname: lastname,
        phone: phone,
      });

    console.log(updatedResult);

    res.status(201).send({
      returnCode: 201,
      message: 'Successfully edited User details',
      type: 'Success',
    });

  } catch (error) {
    console.log(error);
    res.status(400).json({
      returnCode: 400,
      message: 'Error while editing User details',
      type: 'Error',
    });
  }
};

module.exports = { listUsers, getUser, loginUser, signUpUser, editUser };
