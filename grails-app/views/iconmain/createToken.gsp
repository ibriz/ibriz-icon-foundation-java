<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Welcome to Grails</title>

    <asset:link rel="icon" href="favicon.ico" type="image/x-ico"/>
</head>

<body>
<content tag="nav">
    <li class="dropdown">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
           aria-expanded="false">ICON Operations <span class="caret"></span></a>
        <ul class="dropdown-menu">
            <li>
                <a href='<g:createLink controller="iconmain" action="createToken"
                                       params="${[address: address, tokenType: 'IDOL']}"/>'>Create Token (IDOL)</a>
            </li>
            <li>
                <a href='<g:createLink controller="iconmain" action="checkAccountPage"
                                       params="${[address: address, tokenType: 'IDOL']}"/>'>My Wallet (IDOL)</a>
            </li>
            <li>
                <a href='<g:createLink controller="iconmain" action="transferIdolToken"
                                       params="${[address: address, tokenType: 'IDOL']}"/>'>Transfer Idol Token (IDOL)</a>
            </li>
            <li role="separator" class="divider"></li>
        </ul>
    </li>
    <li class="dropdown">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
           aria-expanded="false">About<span class="caret"></span></a>
        <ul class="dropdown-menu">
            <li><a href="#">ICON Foundation</a></li>
            <li><a href="#">ICON Token (ICX)</a></li>
            <li><a href="#">Sample Token</a></li>
        </ul>
    </li>
</content>

<div class="svg" role="presentation">
    <div class="grails-logo-container">
        <asset:image src="icon_logo.svg" class="grails-logo"/>
    </div>
</div>

<div id="content" role="main">
    <section class="row colset-2-its">
        <g:form controller="iconmain" action="checkAccountPage">

            <label class="idolLabel">Owner's Address</label> ${address}<br>
            <label class="idolLabel">SCORE Address (Current)</label> ${scoreAddress}<br/>
            <label class="idolLabel">Token Symbol (Current)</label> ${tokenType}<br/>
            <label class="idolLabel">First Name</label>
            <g:textField class="idolInput" name="name"/><br/>
            <label class="idolLabel">Age</label>
            <g:textField class="idolInput" name="age"/><br/>
            <label class="idolLabel">Gender</label>
            <g:textField class="idolInput" name="gender"/><br/>
            <label class="idolLabel">IPFS Handle</label>
            <g:textField class="idolInput" name="ipfs_handle"/><br/>

            <g:actionSubmit value="Create Idol Token"/>
        </g:form>

    </section>
</div>

</body>
</html>
