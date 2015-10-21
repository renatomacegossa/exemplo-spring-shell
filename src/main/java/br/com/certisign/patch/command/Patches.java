package br.com.certisign.patch.command;

import br.com.certisign.patch.repo.PessoaRepo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliAvailabilityIndicator;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.stereotype.Component;

@Component
public class Patches implements CommandMarker {

    private static final Logger log = Logger.getLogger( Patches.class );

    @Autowired
    private PessoaRepo repo;

    @CliAvailabilityIndicator( { "!", "//", "date", "help", "script", "system", "version" } )
    public boolean isAvailable() {
        return false;
    }

    @CliCommand( value = "patches apply crlPatch", help = "..." )
    public String patches_apply_crlPatch(
            //@CliOption( key = "caId", mandatory = true ) final Long caId
    ) {

        StringBuilder builder = new StringBuilder();

        String str = "--- PATCH LIST CRLS";
        log.info( str );
        builder.append( str ).append( "\n" );

        return builder.toString();
    }
}