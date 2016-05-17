using System;
using System.Diagnostics;
using Tweetinvi;
using Tweetinvi.Core.Credentials;

namespace AnalisisOpinionTwitter
{
    class Program
    {
        static void Main(string[] args)
        {
            // Aplicando credenciales. Si se utilizan por primera vez, set up the ApplicationCredentials
            Auth.SetUserCredentials("Consumer Key", //Consumer Key
                "Consumer Secret", //Consumer Secret
                "Access Token", //Access Token
                "Access Token Secret"); //Access Token Secret

            // Si hay un nuevo tema creado, credenciales por defecto de API twitter Application Credentials
            Auth.ApplicationCredentials = new TwitterCredentials(
                "Consumer Key", 
                "Consumer Secret", 
                "Access Token", 
                "Access Token Secret");

            
            Stream_FilteredStreamExample();
           
        }

        private static void Stream_FilteredStreamExample()
        {
            for (;;)
            {
                try
                {
                    HBaseWriter hbase = new HBaseWriter();
                    var stream = Stream.CreateFilteredStream();
                    stream.AddLocation(Geo.GenerateLocation(-180, -90, 180, 90));

                    var tweetCount = 0;
                    var timer = Stopwatch.StartNew();

                    stream.MatchingTweetReceived += (sender, args) =>
                    {
                        tweetCount++;
                        var tweet = args.Tweet;

                        // Escribiendo Tweets en la HBase
                        hbase.WriteTweet(tweet);

                        if (timer.ElapsedMilliseconds > 1000)
                        {
                            if (tweet.Coordinates != null)
                            {
                                Console.ForegroundColor = ConsoleColor.Green;
                                Console.WriteLine("\n{0}: {1} {2}", tweet.Id, tweet.Language.ToString(), tweet.Text);
                                Console.ForegroundColor = ConsoleColor.White;
                                Console.WriteLine("\tLocation: {0}, {1}", tweet.Coordinates.Longitude, tweet.Coordinates.Latitude);
                            }

                            timer.Restart();
                            Console.WriteLine("\tTweets/sec: {0}", tweetCount);
                            tweetCount = 0;
                        }
                    };

                    stream.StartStreamMatchingAllConditions();
                }
                catch (Exception ex)
                {
                    Console.WriteLine("Exception: {0}", ex.Message);
                }
            }
        }
    }
}
