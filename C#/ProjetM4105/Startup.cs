using Microsoft.Owin;
using Owin;

[assembly: OwinStartupAttribute(typeof(ProjetM4105.Startup))]
namespace ProjetM4105
{
    public partial class Startup
    {
        public void Configuration(IAppBuilder app)
        {
            ConfigureAuth(app);
        }
    }
}
