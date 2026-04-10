import { schedule } from 'danger'
const reporter = require("danger-plugin-lint-report");

// Checkstyle lints
schedule(
  reporter.scan({
    /**
     * File mask used to find XML checkstyle reports.
     */
    fileMask: "**/reports/**/*.xml",

    /**
     * If set to true, the severity will be used to switch between the different message formats (message, warn, fail).
     */
    reportSeverity: true,

    /**
     * If set to true, only issues will be reported that are contained in the current changeset (line comparison).
     * If set to false, all issues that are in modified files will be reported.
     */
    requireLineModification: false,

    /**
     * Optional: If set to true, it will remove duplicate violations.
     */
    removeDuplicates: true,
  })
);
