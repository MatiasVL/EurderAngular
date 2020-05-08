const express = require('express');
const app = express();

app.use(express.static('./dist/eurder'));

app.listen(process.env.PORT || 8080);
