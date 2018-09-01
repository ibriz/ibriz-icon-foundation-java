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
        <hr/>
        <label class="idolLabel">Accounts:</label><br/>
        <g:each var="accounts" in="${accountList}">
            <p><a href='<g:createLink controller="iconmain" action="checkAccountPage"
                                      params="${[address: accounts, tokenType: 'IDOL']}"/>'>
                ${accounts}
            </a>
            </p><br/>
        </g:each>

        <hr/>

        <label class="idolLabel">Owner's Address:</label> ${address}<br/>
        <label class="idolLabel">SCORE Address:</label> ${scoreAddress}<br/>
        <label class="idolLabel">Token Balance:</label> ${tokenBalance}<br/>
        <label class="idolLabel">Token Symbol:</label> ${tokenType}<br/>
        <label class="idolLabel">ICX Balance:</label> ${ICXbalance}<br/><hr/>
        <label class="idolLabel">List of Tokens:</label><br/>
        <hr/>
        <g:each var="token" in="${tokenList}">
            <label class="idolLabel">TokenId:</label><a
                href='<g:createLink controller="iconmain" action="checkTokenDetails"
                                    params="${[address: address, tokenType: 'IDOL', tokenId: token.tokenId]}"/>'>${token.tokenId}</a><br/>
            <label class="idolLabel">Age:</label>${token.age}<br/>
            <label class="idolLabel">Gender:</label>${token.gender}<br/>
            <label class="idolLabel">Ipfs Handle:</label>${token.ipfs_handle}<br/>
            <label class="idolLabel">Token Owner:</label>${token.owner}<br/>
            <hr/>
        </g:each><br/>
    </section>
</div>

</body>
</html>
