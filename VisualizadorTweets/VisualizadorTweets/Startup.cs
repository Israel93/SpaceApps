using Microsoft.Owin;
using Owin;

[assembly: OwinStartupAttribute(typeof(VisualizadorTweets.Startup))]
namespace VisualizadorTweets
{
    public partial class Startup
    {
        public void Configuration(IAppBuilder app)
        {
            ConfigureAuth(app);
        }
    }
}
