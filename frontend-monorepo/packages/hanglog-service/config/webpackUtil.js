/* eslint-disable @typescript-eslint/no-var-requires */
const fs = require('fs');
const path = require('path');

const cwdAbsolutePath = fs.realpathSync(process.cwd());
const convertToAbsolutePath = (paths) => path.resolve(cwdAbsolutePath, paths);

module.exports = { convertToAbsolutePath };
