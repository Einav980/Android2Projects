const toTitleCase = (str) => {
  return str[0].toUpperCase() + str.toLowerCase().substr(1);
};

module.exports = { toTitleCase };
