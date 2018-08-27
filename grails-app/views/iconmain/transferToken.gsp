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
                <a href='<g:createLink controller="iconmain" action="myWallet" params="${[address: address, tokenType: tokenType]}"/>'>My Wallet</a>
            </li>
            <li>  <a href='<g:createLink controller="iconmain" action="transferToken" params="${[address: address, tokenType: tokenType]}"/>'>Transfer</a></li>
            <li role="separator" class="divider"></li>
        </ul>
    </li>
    <li class="dropdown">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
           aria-expanded="false">About<span class="caret"></span></a>
        <ul class="dropdown-menu">
            <li><a href="#1">ICON Foundation</a></li>
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

        <g:form controller="iconmain" action="transfer">
            <label>Owner's Address:</label>
            <g:field name="fromAddress" readonly="readonly" value="${address}" style="width: 500px"
                     type="text"/><br/>
            <label>Token Type:</label>
            <g:select name="tokenType" from="${scoreMap.keySet()}"/><br/>
            <label>To Address:</label>
            <g:textField name="toAddress" style="width: 500px"/><br/>
            <label>Tokne Amount:</label>
            <g:textField name="tokenAmount" style="width: 500px"/><br/>
            <g:actionSubmit value="Transfer"/>
        </g:form>

    </section>
</div>

</body>
</html>
