using System.Collections.Generic;
using System.Web.Http;

using System.Threading.Tasks;
using VisualizadorTweets.Models;

namespace VisualizadorTweets.Controllers
{
    public class TweetsController : ApiController
    {
        HBaseReader hbase = new HBaseReader();

        public async Task<IEnumerable<Tweet>> GetTweetsByQuery(string query)
        {
            return await hbase.QueryTweetsByKeywordAsync(query);
        }
    }
}
