#Test Application [![Build Status](https://travis-ci.org/shanika/testapp.svg?branch=master)](https://travis-ci.org/shanika/testapp)

##Build Results (Including unit tests)

[https://travis-ci.org/shanika/testapp.svg?branch=master](https://travis-ci.org/shanika/testapp)

##Deployed application

[Demo App](http://52.62.31.206)



* I have used travis-ci to build the application and run tests. (free usage)
* I have used AWS Elastic Beanstalk tool to quickly deploy a working instance of my application to an Amazon Web Services EC2 instance.

The UI is provided just to play around with REST API. Following REST API is available to test.

|Oparation | HTTP Method | url |
|----------|-------------|-----|
|Create |		POST |	/api/customers |
|Update |		PUT |	/api/customers/:id |
|Read |		GET |	/api/customers/:id |
|ReadAll |		GET |	/api/customers/ |
|DELETE |		DELETE |	/api/customers/:id |




