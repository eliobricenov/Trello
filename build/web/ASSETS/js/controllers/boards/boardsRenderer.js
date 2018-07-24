$.validator.addMethod("lettersonlys", function(value, element) {
    return this.optional(element) || /^[A-Za-z](([a-zA-Z])*[^#&<>\"~;$^%{}?]{1,20}?)*$/.test(value);
}, "Invalid format. Example: MyBoard, MyBoard 1, My_Board_1");

function renderNavBar(username, container) {
    // //tag creation (first in, first out)
    // let nav = document.createElement("nav");
    // let navDiv = document.createElement("div");
    // let navLink = document.createElement("a");
    // //attributes assignment
    // navDiv.className = "navbar-fixed";
    // navLink.className = "brand-logo center";
    // //appending
    // container.appendChild(nav);
    // nav.appendChild(navDiv);
    // navDiv.appendChild(navLink);
    // navLink.appendChild(document.createTextNode(`${username}`));
    // tag creation (first in, first out)
    // let navDiv = document.createElement("div");
    // let nav = document.createElement("nav"); 
    // let div_logo = document.createElement("div"); 
    // let a_logo = document.createElement("a");
    // let ul = document.createElement("ul");
    // let li = document.createElement("li");
    // let btn_ul = document.createElement("btn");
    // let a_ul = document.createElement("a");
    // let form = document.createElement("form");
    // let div_input = document.createElement("div");
    // let input = document.createElement("input");
    // let label = document.createElement("label");
    // let i_input = document.createElement("i");
    // let i_close = document.createElement("i");
    // let div_label = document.createElement("div");
    // //attributes assignment
    // navDiv.className="navbar-fixed";
    // div_logo.className="nav-wrapper blue-grey";
    // a_logo.className="brand-logo";
    // a_logo.href="#";
    // a_logo.style.textDecoration = "none";
    // ul.className="right hide-on-med-and-down";
    // btn_ul.className= "boton";
    // a_ul.className="btn waves-effect waves-light red lighten-2";
    // a_ul.setAttribute("onclick", "logout()");
    // form.className="hide-on-med-and-down";
    // form.setAttribute("id","searchForm");
    // div_input.className="input-field";
    // div_input.setAttribute("id","search2");
    // div_input.style.maxWidth = "300pt";
    // input.setAttribute("id" , "search");
    // input.setAttribute("type" , "search");
    // input.setAttribute("required" , true);
    // label.className="label-icon";
    // label.setAttribute("for" , "search");
    // i_input.className="material-icons";
    // i_close.setAttribute("id" , "close");
    // i_close.className="material-icons";
    // div_label.setAttribute("id" , "searchResults");
    // //appending
    // a_logo.appendChild(document.createTextNode("Jellio"));
    // a_ul.appendChild(document.createTextNode("Log out"));
    // btn_ul.appendChild(a_ul);
    // li.appendChild(btn_ul);
    // ul.appendChild(li);
    // i_input.appendChild(document.createTextNode("search"));
    // label.appendChild(i_input);
    // i_close.appendChild(document.createTextNode("close"));
    // div_input.appendChild(input);
    // div_input.appendChild(label);
    // div_input.appendChild(i_close);
    // div_input.appendChild(div_label);
    // form.appendChild(div_input);
    // div_logo.appendChild(a_logo);
    // div_logo.appendChild(ul);
    // div_logo.appendChild(form);
    // nav.appendChild(div_logo);
    // navDiv.appendChild(nav);
    // container.appendChild(navDiv);
    $(container).html(`<div class="navbar-fixed blue-grey">
            <nav>
                <div class="nav-wrapper blue-grey">
                    <a class="brand-logo hide-on-med-and-down" style="text-decoration:none">Jellio</a>
                    <div class="searchBar">
                        <div class="input-field">
                            <input id="search" type="search" name="search">
                            <label  class="label-icon" for="search"><i class="material-icons">search</i></label>
                            <i id="close_icon" class="material-icons">close</i>
                        </div>
                    </div>
                    <ul class="right">
                        <li><a onclick="logout()" class="btn waves-effect waves-light red lighten-2">Log out</a></li>
                    </ul>
                </div>
            </nav>
        </div>`);
    
    $('#search').on('keyup', function(e) {
        if (e.keyCode === 13 && $(this).val() !== '') {
            window.location.href = `/Trello/views/search.html?token=${$(this).val()}`
        }
    });
    $('#close_icon').on("click", function() {
        $('#search').val('');
    })
}

function renderFloatingButton(container) {
    //tag creation 
    let mainDiv = document.createElement("div");
    let mainB = document.createElement("a");
    let create_icon = document.createElement("i");
    let ul = document.createElement("ul");
    let li = document.createElement("li");
    let createB = document.createElement("a");
    let add_icon = document.createElement("i");
    //attributes assignment
    mainDiv.className = "fixed-action-btn";
    mainB.className = "btn-floating btn-large blue";
    mainB.setAttribute("id", "showButton");
    create_icon.className = "material-icons"
    createB.className = "btn-floating red modal-trigger";
    createB.setAttribute("id", "createButton");
    createB.href = "#modal1";
    add_icon.className = "material-icons";
    //appending
    container.appendChild(mainDiv);
    mainDiv.appendChild(mainB);
    mainB.appendChild(create_icon);
    create_icon.appendChild(document.createTextNode("create"));
    mainDiv.appendChild(ul);
    ul.appendChild(li);
    li.appendChild(createB);
    createB.appendChild(add_icon);
    add_icon.appendChild(document.createTextNode("add"));
}

function renderBoard(container, data) {
    //tag creation
    let collectionItem = document.createElement("li");
    let avatarIcon = document.createElement("i");
    let title = document.createElement("a");
    let description = document.createElement("p");
    let id = document.createElement("span");
    let collabs = document.createElement("span");
    let optionsLink = document.createElement("a");
    let deleteLink = document.createElement("a");
    let optionsIcon = document.createElement("i");
    let deleteIcon = document.createElement("i");
    //attributes assignment 
    collectionItem.className = "collection-item avatar";
    avatarIcon.className = "material-icons circle";
    avatarIcon.style.backgroundColor = data.board_color;
    title.className = "title";
    title.href = "/Trello/views/columns.html?board_id=" + data.board_id;
    description.className = "paragraph";
    optionsLink.setAttribute('data-target', 'modal_update');
    id.className = "id";
    id.hidden = true;
    collabs.className = "collabs";
    collabs.hidden = true;
    optionsLink.className = "secondary-content modal-trigger";
    deleteLink.className = "secondary-content modal-trigger";
    deleteLink.href = "#";
    optionsIcon.className = "material-icons";
    deleteIcon.className = "material-icons";
    //appending
    avatarIcon.appendChild(document.createTextNode("event_note"));
    title.appendChild(document.createTextNode(data.board_name));
    description.appendChild(document.createTextNode(data.board_description));
    id.appendChild(document.createTextNode(data.board_id));
    optionsIcon.appendChild(document.createTextNode("edit"));
    deleteIcon.appendChild(document.createTextNode("delete"));
    optionsLink.appendChild(optionsIcon);
    deleteLink.appendChild(deleteIcon);
    collabs.appendChild(document.createTextNode(JSON.stringify(data.board_collaborators)));
    collectionItem.appendChild(avatarIcon);
    collectionItem.appendChild(title);
    collectionItem.appendChild(description);
    collectionItem.appendChild(id);
    collectionItem.appendChild(collabs);
    collectionItem.appendChild(optionsLink);
    container.appendChild(collectionItem);
}

function renderFoundBoards(container, data) {
    //tag creation
    let collectionItem = document.createElement("li");
    let avatarIcon = document.createElement("i");
    let title = document.createElement("a");
    let description = document.createElement("p");
    let id = document.createElement("span");
    let collabs = document.createElement("span");
    //attributes assignment 
    collectionItem.className = "collection-item avatar";
    avatarIcon.className = "material-icons circle";
    avatarIcon.style.backgroundColor = data.board_color;
    title.className = "title";
    title.href = "/Trello/views/columns.html?board_id=" + data.board_id;
    description.className = "paragraph";
    id.className = "id";
    id.hidden = true;
    collabs.className = "collabs";
    collabs.hidden = true;
    //appending
    avatarIcon.appendChild(document.createTextNode("event_note"));
    title.appendChild(document.createTextNode(data.board_name));
    description.appendChild(document.createTextNode(data.board_description));
    id.appendChild(document.createTextNode(data.board_id));
    collabs.appendChild(document.createTextNode(JSON.stringify(data.board_collaborators)));
    collectionItem.appendChild(avatarIcon);
    collectionItem.appendChild(title);
    collectionItem.appendChild(description);
    collectionItem.appendChild(id);
    collectionItem.appendChild(collabs);
    container.appendChild(collectionItem);
}