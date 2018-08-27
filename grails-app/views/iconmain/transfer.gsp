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
            <li><a href="#">ICON Foundation</a></li>
            <li><a href="#">ICON Token (ICX)</a></li>
            <li><a href="#">Sample Token</a></li>
        </ul>
    </li>
</content>

<div class="svg" role="presentation">
    <div class="grails-logo-container">
        <asset:image src="icon_logo.svg.svg" class="grails-logo"/>
    </div>
</div>

<div id="content" role="main">
    <section class="row colset-2-its">
        <b>Owner's Address:</b> ${address}<br/>
        <b>To Address:</b> ${toAddress}<br/>
        <b>SCORE Address:</b> ${scoreAddress}<br/>
        <b>Amount Transfer:</b> ${transferAmount}<br/>
        <b>Transaction Hash:</b> ${transactionHash}<br/>
        <b>Token Symbol:</b> ${tokenSymbol}<br/>
        <b>Remaining Balance:</b> ${remainingBalance}<br/>
        <b>Initial Balance:</b> ${tokenBalance}<br/>
        <b>ICX Balance:</b> ${ICXbalance}<br/>
    </section>
</div>

</body>
</html>
