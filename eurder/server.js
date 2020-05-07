const express = require('express');
const application = express();

application.use(express.static('./dist/eurder'));

application.listen(process.env.PORT || 8080);
