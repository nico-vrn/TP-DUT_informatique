using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Web.Routing;
using ProjetM4105.Data.Services;
using ProjetM4105.Data.Models;
using Autofac;
using Autofac.Integration.Mvc;

namespace ProjetM4105.App_Start
{
    public class ContainerConfig : Controller
    {
        internal static void RegisterContainer()
        {
            var builder = new ContainerBuilder();
            builder.RegisterControllers(typeof(MvcApplication).Assembly);

            // Registering custom dataprovider class
            builder.RegisterType<SqlDishDataProvider>()
                                .As<IDishData>()
                                .InstancePerRequest();
            builder.RegisterType<PlatContext>().InstancePerRequest();

            var container = builder.Build();
            DependencyResolver.SetResolver(new AutofacDependencyResolver(container));

        }
    }
}