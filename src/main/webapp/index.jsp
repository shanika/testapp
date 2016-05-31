<!doctype html>
<html lang="en">
<head>
    <title>Vodafone Admin</title>
    <link rel="stylesheet" href="static/lib/ng-admin.min.css">
</head>
<body ng-app="app">
<div ui-view></div>
<script src="static/lib/ng-admin.min.js"></script>
<script type="text/javascript">

    var app = angular.module('app', ['ng-admin']);

    app.config(['NgAdminConfigurationProvider', function(NgAdminConfigurationProvider) {
        var nga = NgAdminConfigurationProvider;
        var admin = nga.application('Vodafone Admin').baseApiUrl('/api/');
        var customers = nga.entity('customers');

        customers.listView().fields([
            nga.field('name'),
            nga.field('address'),
            nga.field('phone')
        ]).listActions(['show', 'edit', 'delete']);

        customers.creationView().fields(customers.listView().fields());
        customers.editionView().fields(customers.listView().fields()).title('Edit Customer Details');
        customers.showView().fields(customers.listView().fields()).title('Customer Details');
        customers.deletionView().title('Delete Customer {{ entry.values.name }}')

        admin.addEntity(customers);
        nga.configure(admin);
    }]);
</script>
</body>
</html>