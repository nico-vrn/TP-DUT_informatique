using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Data.Entity;

namespace ProjetM4105.Data.Models
{
    public class PlatsDataProvider : DropCreateDatabaseAlways<PlatContext>
    {
        protected override void Seed(PlatContext context)
        {
            GetPlats().ForEach(p => context.Plats.Add(p));
        }

        private static List<Plats> GetPlats()
        {
            var plats = new List<Plats> {
                new Plats
                {
                    PlatID = 1,
                    PlatName = "Salade César",
                    Description = "A Caesar salad consists of chopped romaine lettuce and croutons tossed in a dressing of lemon juice, olive oil, egg, Worcestershire sauce, garlic, and Parmesan cheese.",
                    ImagePath="~/Content/Images/saladecesar.jpg",
                    UnitPrice =  10.69,
                    Category = Category.Entree
               },
                new Plats
                {
                    PlatID = 2,
                    PlatName = "Assortiments de charcuterie",
                    Description = "Charcuterie is a type of French cuisine that centers around meat (usually pork) products such as ham, bacon, and sausage.",
                    ImagePath="~/Content/Images/assortimentcharcuterie.jpg",
                    UnitPrice = 15.69,
                     Category = Category.Entree
               },
                new Plats
                {
                    PlatID = 3,
                    PlatName = "Amuse bouche",
                    Description = "Let us know what you think of our Aperitif Cocktail. We love to hear from you.",
                    ImagePath="~/Content/Images/amusebouche.jpg",
                    UnitPrice = 5.69,
                    Category = Category.Entree
                },
                new Plats
                {
                    PlatID = 4,
                    PlatName = "Gratin dauphinois",
                    Description = "A gratin is a dish that is usually made with potatoes and cheese. It is often baked in the oven with a layer of bread crumbs on top.",
                    ImagePath="~/Content/Images/gratin.jpeg",
                    UnitPrice = 20.69,
                    Category = Category.Plat
                },
                new Plats
                {
                    PlatID = 5,
                    PlatName = "Burger Raclette",
                    Description = "A raclette burger is a burger topped with melted raclette cheese." +
                                  "No batteries required.",
                    ImagePath="~/Content/Images/burgeraclette.jpg",
                    UnitPrice = 18.69,
                    Category = Category.Plat
                },
                new Plats
                {
                    PlatID = 6,
                    PlatName = "Tartiflette",
                    Description = "A tartiflette is a French dish that is typically made with potatoes, onions, and bacon. The dish is often served with a salad or as a side dish.",
                    ImagePath="~/Content/Images/tartiflette.jpg",
                    UnitPrice = 25.69,
                    Category = Category.Plat
                },
                new Plats
                {
                    PlatID = 7,
                    PlatName = "Ice tea",
                    Description = "A ice tea is a cold beverage that typically consists of tea, sugar, and water.",
                    ImagePath="~/Content/Images/icetea.jpg",
                    UnitPrice = 4.69,
                    Category = Category.Boisson
                },
                new Plats
                {
                    PlatID = 8,
                    PlatName = "Eau",
                    Description = "A water is a molecule composed of two hydrogen atoms and one oxygen atom.",
                    ImagePath="~/Content/Images/eau.jpg",
                    UnitPrice = 105.69,
                    Category = Category.Boisson
                },
                new Plats
                {
                    PlatID = 9,
                    PlatName = "Oasis",
                    Description = "A oasis drink is a type of drink that is made with fruit juices and other flavorings.",
                    ImagePath="~/Content/Images/oasis.jpg",
                    UnitPrice = 4.69,
                    Category = Category.Boisson
                },
                new Plats
                {
                    PlatID = 10,
                    PlatName = "Tiramisu",
                    Description = "A tiramisu is a coffee-flavored Italian dessert. It consists of ladyfingers dipped in coffee, layered with a whipped mixture of eggs, sugar, and mascarpone cheese",
                    ImagePath="~/Content/Images/tiramisu.jpeg",
                    UnitPrice = 7.69,
                    Category = Category.Dessert
                },
                new Plats
                {
                    PlatID = 11,
                    PlatName = "Ile flottante",
                    Description = "A dessert floating island is a dessert that consists of a small island of meringue floating in a custard or syrup.",
                    ImagePath="~/Content/Images/ileflottante.jpg",
                    UnitPrice = 5.69,
                    Category = Category.Dessert
                },
                new Plats
                {
                    PlatID = 12,
                    PlatName = "Cookies",
                    Description = "A cookies dessert is a dessert that is made with cookies.",
                    ImagePath="~/Content/Images/cookies.jpg",
                    UnitPrice = 4.69,
                    Category = Category.Dessert
                },
            };

            return plats;
        }
    }
}
