key=""; //clés API shodan
var bt_search=document.getElementById("btn-lancer-recherche");
bt_search.addEventListener("click",rechercher);
ch_search=document.getElementById("champs_recherche");
ch_search.focus();
ch_search.addEventListener("keydown",event=>{ //faire en sorte de recherche avec entrée
    if(event.keyCode==13)
        rechercher();
})
var recherche=document.getElementById("champs_recherche").value="";

son_ip() //récupération de l'IP
function son_ip(){
    var IP = "https://api.shodan.io/tools/myip?"+key;
    request(IP,afficher_IP)
}

function afficher_IP(data){ //affiche l'IP de l'utilisateur avec un lien de recherche
  document.getElementById("bloc-gif-attente").style.display="none";
  var response=JSON.parse(data.contents);
  var IP=document.getElementById("mon_ip");
  IP.textContent="Votre IP est: "+response;
  IP.addEventListener("click",function(){
    document.getElementById("champs_recherche").value=response;
    rechercher();
  });
}

function rechercher(){ //fonction de recherche
    //console.log("recherche en cours");
    recherche=document.getElementById("champs_recherche").value;
    var url = "https://api.shodan.io/shodan/host/search?" + key + "&query=" + recherche + "&facets=country";
    var url2="https://api.shodan.io/shodan/host/search?key=gFBcaogOaUPDCRBk233ljdeZ0ihfD7Fm&query=country:france&facets=country"
    var url3="https://api.shodan.io/shodan/host/count?key=gFBcaogOaUPDCRBk233ljdeZ0ihfD7Fm&query=port:22&facets=org,os"; // fonctionne retourne le nb de host par pays
    if(recherche[0]==undefined){ //si la recherche est vide
      document.getElementById('empty').textContent="le champs de recherche est vide";
    }
    else if(recherche[0]=="w"){ //si la recherche est un domaine
        var info_domaine="https://api.shodan.io/dns/domain/"+recherche+"?"+key+"&type=A";
        var IP_domaine="https://api.shodan.io/dns/resolve?hostnames="+recherche+"&"+key; //retourne l'IP associé à un domaine
        var autre="domaine";
        request(IP_domaine, afficher_resultat,autre);
    }
    else{ //si la recherche est une IP ou erreur
        var info_IP="https://api.shodan.io/shodan/host/"+recherche+"?"+key; // fonctionne retourne les infos de l'IP
        var autre="IP";
        request(info_IP,afficher_resultat,autre);
    }
    favoris();
}

function afficher_resultat(data,autre){ //affiche les résultats de la recherche
    document.getElementById("bloc-gif-attente").style.display="none";
    var response=JSON.parse(data.contents);
    var cles=Object.keys(response); 
    if (response.error){ //si erreur
        document.getElementById('empty').textContent="Aucun résultat trouvé réessayez";
    }
    else{ //si pas d'erreur
        if (autre=="IP"){ //si recherche est une IP
            var resultat=[response.country_name,response.city, response.org, response.domains, response.ports];
            var noms=["pays","ville","Organisation", "domaines", 'ports'];
            affichage(resultat,noms);
            initMap(response.latitude,response.longitude);
            document.getElementById("map").style.display="block";
            document.getElementById("text_map").style.display="block";
        }
        else{
            //ancienne récupération des sous domaines pas juger utile à implémanter

            /*var sous_domaine=[];  
            for (var i=0;i<response.subdomains.length;i++){
              sous_domaine.push(response.subdomains[i]);
            }
            var resultat=[response.domain, sous_domaine.length];*/
            for (var resultat in response){ //récupération des résultats
              if(response[resultat]==null){ //si résultat est null
                //console.log(response[resultat]);
                resultat=["Aucun résultat trouvé réessayez"];
                noms=[""];
              }
              else{ //si résultat n'est pas null
                var resultat=[cles[0],response[resultat]];
                var noms=["domaine", "IP"];
              }
            }
            affichage(resultat,noms);
        }
    }
}

function affichage(resultat,noms){ //affiche les résultats de la recherche
    var nouveau; var bloc_result=document.getElementById("bloc-resultats");
    for (var i=0;i<resultat.length;i++){ //pour chaque résultat
        nouveau=document.createElement("p");
        nouveau.textContent=noms[i]+": "+resultat[i];
        nouveau.id=noms[i];
        bloc_result.appendChild(nouveau);
    }
    if(noms[1]=="IP" && resultat[1]!=null){   //si recherche est une IP et que l'IP est trouvée
      var pIP=document.getElementById("IP");
      var btIP=document.createElement("button");
      btIP.textContent=" Voir les informations sur cette IP";
      btIP.addEventListener("click",function(){ //si on click affiche les informations sur l'IP
        document.getElementById("champs_recherche").value=resultat[1];
        rechercher();});
      btIP.style.backgroundColor="grey";
      pIP.appendChild(btIP);

    }
}

function request(url, retour, autre){ //requete ajax
    clean();
    document.getElementById("bloc-gif-attente").style.display="block";
    fetch(`https://api.allorigins.win/get?url=${encodeURIComponent(url)}`)
    .then(response => { 
      if (response.ok) return response.json()
      throw new Error('Network response was not ok.')
    })
    .then(data => retour(data,autre));
}

function clean(){ //clean les résultats de la recherche
    var bloc_result=document.getElementById("bloc-resultats");
    while (bloc_result.firstChild) { //supprime les résultats précédents
        bloc_result.removeChild(bloc_result.firstChild);
    }
    document.getElementById("empty").textContent="";
    document.getElementById("map").style.display="none";
    document.getElementById("text_map").style.display="none";
}

var map = null;
function initMap(lat,lon) { //fonction d'initialisation de la carte
    // Créer l'objet "map" et l'insèrer dans l'élément HTML qui a l'ID "map"
	map = new google.maps.Map(document.getElementById("map"), {
        // Nous plaçons le centre de la carte avec les coordonnées ci-dessus
        center: new google.maps.LatLng(lat, lon),
        // Nous définissons le zoom par défaut
        zoom: 12,
        // Nous définissons le type de carte (ici carte routière)
        mapTypeId: google.maps.MapTypeId.ROADMAP,
        // Nous activons les options de contrôle de la carte (plan, satellite...)
        mapTypeControl: true,
        // Nous désactivons la roulette de souris
        scrollwheel: false,
        mapTypeControlOptions: {
            // Cette option sert à définir comment les options se placent
            style: google.maps.MapTypeControlStyle.HORIZONTAL_BAR
        },
        // Activation des options de navigation dans la carte (zoom...)
        navigationControl: true,
        navigationControlOptions: {
            // Comment ces options doivent-elles s'afficher
            style: google.maps.NavigationControlStyle.ZOOM_PAN
        }

    });// Nous ajoutons un marqueur
    var marker = new google.maps.Marker({
        // Nous définissons sa position (syntaxe json)
        position: {lat: lat, lng: lon},
        // Nous définissons à quelle carte il est ajouté
        map: map
    });
}


//gestion des favoris

favoris();
document.addEventListener('keyup', function(e) {favoris();});

function favoris(){ //gestion des favoris
  if(localStorage.fav==undefined){ //si pas de localStorage
    localStorage.fav=JSON.stringify({favoris:[]});
  }
  var loupe=document.getElementById("btn-favoris");
  var fav=recup_fav();
  var elmt_fav=fav.favoris;
  if (elmt_fav.length==0){ //si pas de favoris
    etoile("vide");
    loupe.addEventListener("click",ajouter_fav);
    return;
  }
  else{ //si des favoris
    for (var i=0;i<fav.favoris.length;i++){
      if (elmt_fav[i]==document.getElementById("champs_recherche").value){ //si le champ de recherche est déjà dans les favoris
        etoile("pleine");
        loupe.removeEventListener("click",ajouter_fav);
        loupe.addEventListener("click",function(){
          supprimer_fav("etoile");
        });
        return;
      }
      else{ //si le champ de recherche n'est pas dans les favoris
        etoile("vide");
        loupe.removeEventListener("click",supprimer_fav);
        loupe.addEventListener("click",ajouter_fav);
      }
    }
  }
}

function recup_fav(){ //recupère les favoris
  var fav=JSON.parse(localStorage.fav);
  return fav;
}

function etoile(cote){  //affiche les étoiles
  var loupe=document.getElementById("btn-favoris");
  var img_pleine=document.getElementById("img-pleine");
  var img_vide=document.getElementById("img-vide");
  if(cote=="pleine"){ //si pleine
    img_pleine.style.display="block";
    img_vide.style.display="none";
    loupe.style.backgroundColor="var(--main-green)";
    loupe.style.border=".1em solid grey"
  }
  else{   //si vide
    loupe.style.backgroundColor="grey";
    img_pleine.style.display="none";
    img_vide.style.display="block";
  }
}

function ajouter_fav(){ //ajoute un favoris
  recherche=document.getElementById("champs_recherche").value;
  empty=document.getElementById('empty');
  if(recherche==""){ //si champs de recherche vide
    empty.textContent="Vous ne pouvez pas ajouter rien au favoris";
  }
  else{ //si champs de recherche rempli
    empty.textContent="";
    var fav=recup_fav();
    fav.favoris.push(recherche);
    localStorage.fav=JSON.stringify(fav);
    favoris();
    affiche_fav();
  }
}

function supprimer_fav(cmt){ //supprime un favoris
  //console.log("supprimer_fav");
  var fav=recup_fav();
  var elmt_fav=fav.favoris;
  favoris();
  if (cmt=="etoile"){ //si clic sur étoile
    var elmt_fav_supp=document.getElementById("champs_recherche").value;
  }
  else{ //si clic sur le bouton supprimer
    var elmt_fav_supp=cmt;
  }
  for (var i=0;i<fav.favoris.length;i++){ //pour chaque favoris
    if (elmt_fav[i]==elmt_fav_supp){ //si le favoris à supprimer est le favoris en cours
      if(confirm("êtes vous sur de vouloire supprimer le favoris ?")){
        fav.favoris.splice(i,1);
        localStorage.fav=JSON.stringify(fav);
        favoris()
        affiche_fav();
        return;
      }
    }
    }
}

function affiche_fav(){ //affiche les favoris
  clear_fav(); //efface les favoris
  var liste=document.getElementById("liste-favoris");
  var empty=document.getElementById("info-vide");
  var btn_supp=document.getElementById("btn-supp");
  var fav = recup_fav();
  if (fav.favoris.length==0){ //si pas de favoris
    empty.style.display="block";
    btn_supp.style.display="none";
  }
  else{ //si des favoris
    empty.style.display="none";
    btn_supp.style.display="block";
    btn_supp.addEventListener("click",function(){
      localStorage.clear();
      favoris();
      affiche_fav();
    });
    for (var i=0;i<fav.favoris.length;i++){ //pour chaque favoris
      newLi=document.createElement("li");
      newA=document.createElement("a");
      newA.textContent=fav.favoris[i];
      newA.addEventListener("click",function(){
        document.getElementById("champs_recherche").value=this.textContent;
        rechercher();
      });
      var img_croix=document.createElement("img");
      img_croix.src="images/croix.svg";
      img_croix.title="Supprimer le favoris";
      img_croix.width="22";
      img_croix.setAttribute('id',fav.favoris[i])
      img_croix.addEventListener("click",function(){
        var ID=this.getAttribute('id');
        supprimer_fav(ID);
      });
      newLi.appendChild(newA);
      newLi.appendChild(img_croix);
      liste.appendChild(newLi);
    }
  }
}

function clear_fav(){ //efface les favoris
  var liste=document.getElementById("liste-favoris");
    while (liste.firstChild) {
        liste.removeChild(liste.firstChild);
    }
}


//gestion autocomplete  

function autocomplete(inp, arr) { //fonction autocomplete
  var currentFocus;
  inp.addEventListener("input", function(e) {
      var a, b, i, val = this.value;
      closeAllLists();
      if (!val) { return false;}
      currentFocus = -1;
      a = document.createElement("DIV");
      a.setAttribute("id", this.id + "autocomplete-list");
      a.setAttribute("class", "autocomplete-items");
      var dd = document.getElementById("autocomplete-container");
      dd.appendChild(a);
      for (i = 0; i < arr.length; i++) {
        if (arr[i].substr(0, val.length).toUpperCase() == val.toUpperCase()) {
          b = document.createElement("DIV");
          b.innerHTML = "<strong>" + arr[i].substr(0, val.length) + "</strong>";
          b.innerHTML += arr[i].substr(val.length);
          b.innerHTML += "<input type='hidden' value='" + arr[i] + "'>";
          b.addEventListener("click", function(e) {
              inp.value = this.getElementsByTagName("input")[0].value;
              closeAllLists();
          });
          a.appendChild(b);
        }
      }
  });
  function closeAllLists(elmnt) {
    var x = document.getElementsByClassName("autocomplete-items");
    for (var i = 0; i < x.length; i++) {
      if (elmnt != x[i] && elmnt != inp) {
        x[i].parentNode.removeChild(x[i]);
      }
    }
  }
  document.addEventListener("click", function (e) {
      closeAllLists(e.target);
  });
}
var site = ["www.google.com","www.amazon.fr","www.facebook.com"]

autocomplete(document.getElementById("champs_recherche"), site);